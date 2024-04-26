package src.javasqlriskmanager.singletons;

import src.javasqlriskmanager.models.Department;

public class DepartmentSingleton {

    public static final DepartmentSingleton instance = new DepartmentSingleton();

    private Department department;

    public static DepartmentSingleton getInstance() {
        return instance;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
