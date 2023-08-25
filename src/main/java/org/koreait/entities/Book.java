package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.BookStatus;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Book extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long bookNo;

    @Column(length=60)
    private String category;

    @Column(length=45, nullable = false)
    private String gid;

    @Column(length=100, nullable = false)
    private String bookNm;
    private int price;
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(length=25, nullable = false)
    private BookStatus status;

    @Lob
    private String description;

    private long listOrder;

}
