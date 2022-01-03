package com.softserve.itacademy.model;

import java.util.Objects;

public class Task {

    private String name;

    private Priority priority;

    // Constructor(s), getters, setters, hashCode, equals, etc.
    
    public Task(String name, Priority priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {        
        this.name = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.priority);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || 
                (getClass() != obj.getClass())) {
            return false;
        }
        
        final Task other = (Task) obj;
        
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        
        if (this.priority != other.priority) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {        
        
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
