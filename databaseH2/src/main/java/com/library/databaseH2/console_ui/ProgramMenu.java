package com.library.databaseH2.console_ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ProgramMenu {

    private Map<Integer, UIAction> menuNumberToUiAction = new HashMap<>();

    @Autowired
    public ProgramMenu(List<UIAction> uiActions) {
        menuNumberToUiAction.put(1, findUIAction(uiActions, AddBookUiAction.class));
        menuNumberToUiAction.put(2, findUIAction(uiActions, RemoveBookUiAction.class));
        menuNumberToUiAction.put(3, findUIAction(uiActions, GetAllBooksUiAction.class));
        menuNumberToUiAction.put(4, findUIAction(uiActions, SearchBooksUiAction.class));
        menuNumberToUiAction.put(0, findUIAction(uiActions, ExitUiAction.class));
    }

    public void executeMenuNumber(Integer menuNumber) {
        menuNumberToUiAction.get(menuNumber).execute();
    }

    private UIAction findUIAction(List<UIAction> uiActionList, Class uiActionClass) {
        return uiActionList.stream()
                .filter(uiAction -> uiAction.getClass().equals(uiActionClass))
                .findFirst()
                .get();
    }

    public static void printMenu() {
        System.out.println("Please choose menu number:\n" +
                "1. Add book\n" +
                "2. Delete book\n" +
                "3. Show book list\n" +
                "4. Search books by author or title\n" +
                "0. Exit");
    }

    public static int getMenuNumber() {
        return new Scanner(System.in).nextInt();
    }


}
