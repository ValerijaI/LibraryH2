package com.library.databaseH2.console_ui;

import org.springframework.stereotype.Component;

@Component
public class ExitUiAction implements UIAction {

    @Override
    public void execute() {
        System.out.println("Good bue!");
        System.exit(0);
    }
}
