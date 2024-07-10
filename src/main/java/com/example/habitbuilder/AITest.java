package com.example.habitbuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
public class AITest {
        public static void callWithMessage(String question)
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
            System.out.println(answer);
        }

        public static void main(String[] args){
            while(true){
                try {
                    Scanner scanner = new Scanner(System.in);
                    String question = scanner.nextLine();
                    callWithMessage(question);
                } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
}
