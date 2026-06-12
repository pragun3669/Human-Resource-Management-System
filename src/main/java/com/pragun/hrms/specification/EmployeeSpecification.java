package com.pragun.hrms.specification;

import com.pragun.hrms.entity.Employee;
import com.pragun.hrms.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee>
    hasRole(Role role) {

        return (root, query, cb) ->
                role == null
                        ? null
                        : cb.equal(
                        root.get("role"),
                        role
                );
    }

    public static Specification<Employee>
    hasDepartment(Long departmentId) {

        return (root, query, cb) ->
                departmentId == null
                        ? null
                        : cb.equal(
                        root.get("department")
                                .get("id"),
                        departmentId
                );
    }

    public static Specification<Employee>
    isActive(Boolean isActive) {

        return (root, query, cb) ->
                isActive == null
                        ? null
                        : cb.equal(
                        root.get("isActive"),
                        isActive
                );
    }
}