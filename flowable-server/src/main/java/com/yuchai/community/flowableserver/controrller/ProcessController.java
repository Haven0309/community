package com.yuchai.community.flowableserver.controrller;

import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Haven
 * 2018/6/8 8:14
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/process")
public class ProcessController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Qualifier("processEngine")
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 添加流程
     */
    @PostMapping(value = "/addProcess")
    public Result addProcess(@RequestBody Map<String, Object> map) {
        //启动流程
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("assigneeCode", assigneeCode);
//        map.put("data", data);
//        map.put("creator", data.get("creator"));
//        map.put("desc", data.get("desc"));
        //设置流程创建人
        Authentication.setAuthenticatedUserId("10055606");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("itcommunity", map);
        map.put("taskId",processInstance.getId());
        map.put("instanceId",processInstance.getProcessInstanceId());
        return ResultUtil.genSuccessResult(map);
    }

    /**
     * 获取审批管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Result list(String assigneeCode) {
        if (assigneeCode == null) {
            return ResultUtil.genFailResult("assigneeCode为空");
        } else {
            List<Task> tasks = taskService.createTaskQuery().taskAssignee(assigneeCode).orderByTaskCreateTime().desc().list();
            List list = new ArrayList();
            for (Task task : tasks) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("assignee",task.getAssignee());
                map.put("taskId",task.getId());
                map.put("createTime",task.getCreateTime());
                map.put("description",task.getDescription());
                map.put("name",task.getName());
                map.put("processInstanceId",task.getProcessInstanceId());
                map.put("processDefinitionId",task.getProcessDefinitionId());
                map.put("processVariables",task.getProcessVariables());
                map.put("taskLocalVariables",task.getTaskLocalVariables());
                map.put("myVariable",taskService.getVariable(task.getId(), "data"));
                list.add(map);
            }
            return ResultUtil.genSuccessResult(list);
        }
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "/approve")
    @ResponseBody
    public Result approve(String taskId, String assigneeCode) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ResultUtil.genFailResult("流程不存在");
//            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "y");
        if (assigneeCode == null) {
            return ResultUtil.genFailResult("assigneeCode为空");
        } else {
            map.put("assigneeCode", assigneeCode);
        }

        taskService.complete(taskId, map);
        return ResultUtil.genSuccessResult(map);
    }

    /**
     * 转办
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "/delegate")
    @ResponseBody
    public Result delegateTask(String taskId, String assigneeCode) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ResultUtil.genFailResult("流程不存在");
        }
//        taskService.setAssignee(taskId, assigneeCode);CommandContextUtil

        taskService.delegateTask(taskId, assigneeCode);
        return ResultUtil.genSuccessResult("转办成功");
    }


    /**
     * 拒绝
     */
    @ResponseBody
    @RequestMapping(value = "reject")
    public Result reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "n");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ResultUtil.genFailResult("流程不存在");
        }
        taskService.complete(taskId, map);
        return ResultUtil.genSuccessResult(map);
    }
    /**
     * 终止流程
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public Result delete(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ResultUtil.genFailResult("流程不存在");
        }
        taskService.deleteTask(taskId);
        return ResultUtil.genSuccessResult("流程已经删除");
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
