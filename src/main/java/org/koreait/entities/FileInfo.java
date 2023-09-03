package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(indexes={
        @Index(name="idx_fileinfo_gid", columnList = "gid"),
        @Index(name="idx_fileinfo_gid_location", columnList = "gid,location")
})
/**
 * 파일 정보를 나타내는 엔티티.
 * BaseMemberEntity 클래스를 상속받아 사용하며, 데이터베이스 테이블과 매핑.
 */
public class FileInfo extends BaseMemberEntity {

    @Id
    @GeneratedValue
    private Long id; // 엔티티의 식별자

    @Column(length=45, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 파일 그룹 ID, 기본값은 랜덤한 UUID

    @Column(length=45)
    private String location; // 파일이 저장된 경로

    @Column(length=100, nullable = false)
    private String fileName; // 파일 이름

    @Column(length=45)
    private String extension; // 파일 확장자

    @Column(length=65)
    private String fileType; // 파일의 타입

    private boolean done; // 파일 작업 완료 여부

    @Transient
    private String filePath; // 실 서버 업로드 경로

    @Transient
    private String fileUrl; // 서버 접속 URL

    @Transient
    private String[] thumbsPath; // 썸네일 이미지 경로

    @Transient
    private String[] thumbsUrl; // 썸네일 이미지 접속 URL
}
