package org.koreait.entities;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.commons.constants.BookStatus;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long bookNo;

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cateCd")
    private Category category;

    @Column(length=45, nullable = false)
    private String gid;

    @Column(length=100, nullable = false)
    private String bookNm;
    private int price;
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(length=25, nullable = false)
    private BookStatus status = BookStatus.READY;

    @Lob
    private String description;

    private long listOrder;

    @Transient
    private List<FileInfo> mainImages; // 상품 메인 이미지

    @Transient
    private List<FileInfo> listImages; // 목록 이미지

    @Transient
    private List<FileInfo> editorImages; // 에디터 이미지

}
