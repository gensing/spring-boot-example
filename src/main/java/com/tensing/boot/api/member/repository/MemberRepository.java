package com.tensing.boot.api.member.repository;

import com.tensing.boot.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
