package com.urlShortener.Repository;

import com.urlShortener.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {

    @Query(value = "SELECT * FROM Roles r Where r.name = :name", nativeQuery = true)
    public Role getByName(String name);

    @Query(value = "SELECT * FROM Roles r Where r.id = :id", nativeQuery = true)
    public Role getById(short id);

    @Query(value = "SELECT r.id as id, r.name as name FROM Roles r JOIN User_roles u Where u.user_id = :id and r.id = u.role_id", nativeQuery = true)
    public List<Role> getRolesByUserId(long id);
}