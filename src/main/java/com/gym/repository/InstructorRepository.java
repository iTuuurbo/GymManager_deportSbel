package com.gym.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gym.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor,Integer>{

}