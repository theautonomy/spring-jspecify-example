package com.example.jspecify.nullaway.web;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

    @GetMapping("ui")
    public ModelAndView getMethodName() {
        String viewName = getViewName();
        if (viewName != null) {
            if (StringUtils.hasText(viewName)) {
                return new ModelAndView(viewName);
            }
        }
        return new ModelAndView("index");
    }

    private @Nullable String getViewName() {
        return "index";
    }
}
