package com.example.habitbuilder;


import java.util.*;
import java.util.concurrent.Semaphore;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.json.JSONArray;
import org.json.JSONObject;


public class Qwen2 {
    public static String callWithMessage(String question)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        List<Message> messages = new ArrayList<>();
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(question).build(); //入口
        messages.add(systemMsg);
        messages.add(userMsg);
        GenerationParam param =
                QwenParam.builder().model("qwen2-7b-instruct")
                        .messages(Collections.singletonList(userMsg))
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .build();
        GenerationResult result = gen.call(param);
        String answer=result.getOutput().getChoices().get(0).getMessage().getContent();
        return answer;
    }
    public static String[] streamCallWithMessage(String planTitle,String request)
            throws NoApiKeyException, ApiException, InputRequiredException {

        String prompt = "你是一个计划制定专家，我想要制定一份"+planTitle+"，时长为7天，要求："+request+"，用json的格式给我生成，形式模板如下：{\n" +
                "  \"plan\": [{“day\"：\"第一天\" content:\"跑步50min\"}  ]   }。day为时间，content为当天的计划所有内容，内容丰富一点，只有这两个值，content里面没有别的属性名。只需返回计划主体，不需要多余的表述";

        Generation gen = new Generation();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(prompt).build();
        QwenParam param =
                QwenParam.builder().model("qwen2-7b-instruct")
                        .messages(Collections.singletonList(userMsg))
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .incrementalOutput(true) // get streaming output incrementally
                        .build();
        Flowable<GenerationResult> result = gen.streamCall(param);
        StringBuilder fullContent = new StringBuilder();
        result.blockingForEach(item->{
            fullContent.append(item.getOutput().getChoices().getFirst().getMessage().getContent());
        });
        String jsonData = fullContent.toString();

        JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("plan"); //生成Json数组
        String[] plan = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject dayObject = jsonArray.getJSONObject(i); //生成Json对象来获取其中具体的元素
            String day = dayObject.getString("day");
            String content = dayObject.getString("content");
            plan[i] = day +": "+ content;
        }

//        for (String dayPlan : plan) {
//            System.out.println(day);
//        }
        return plan;
    }



    public static void main(String[] args){
//
        System.exit(0);
    }
}
