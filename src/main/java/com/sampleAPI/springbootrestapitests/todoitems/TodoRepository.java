package com.sampleAPI.springbootrestapitests.todoitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoItems,Integer> {
}