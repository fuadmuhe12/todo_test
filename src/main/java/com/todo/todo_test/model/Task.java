package com.todo.todo_test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class Task {
    private int id;
    private String description;
    private String status;
    private Date dueDate;
}
