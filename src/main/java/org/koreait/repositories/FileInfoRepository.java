package org.koreait.repositories;

import com.querydsl.core.BooleanBuilder;
import org.koreait.entities.FileInfo;
import org.koreait.entities.QFileInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, QuerydslPredicateExecutor<FileInfo> {

    /**
     * 파일 정보를 조회하는 메서드
     * @param gid      그룹 아이디
     * @param location 위치
     * @param mode     조회 모드 ("all": 모든 파일, "done": 완료 파일, "undone": 미완료 파일)
     * @return 조회된 파일 정보 목록
     */
    default List<FileInfo> getFiles(String gid, String location, String mode) {
        QFileInfo fileInfo = QFileInfo.fileInfo;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileInfo.gid.eq(gid));

        if (location != null && !location.isBlank()) {
            builder.and(fileInfo.location.eq(location));
        }

        if ("done".equals(mode)) {
            builder.and(fileInfo.done.eq(true)); // 작업 완료 파일
        } else if ("undone".equals(mode)) {
            builder.and(fileInfo.done.eq(false)); // 미 완료 파일
        }

        List<FileInfo> items = (List<FileInfo>) findAll(builder, Sort.by(asc("createdAt")));

        return items;
    }

    /**
     * 완료, 미완료 파일 모두 조회하는 메서드
     * @param gid      그룹 아이디
     * @param location 위치
     * @return 조회된 파일 정보 목록
     */
    default List<FileInfo> getFiles(String gid, String location) {
        return getFiles(gid, location, "all");
    }

    /**
     * 그룹 아이디로 파일 정보를 조회하는 메서드
     * @param gid 그룹 아이디
     * @return 조회된 파일 정보 목록
     */
    default List<FileInfo> getFiles(String gid) {
        return getFiles(gid, null);
    }

    /**
     * 완료된 파일 정보를 조회하는 메서드
     * @param gid      그룹 아이디
     * @param location 위치
     * @return 조회된 파일 정보 목록
     */
    default List<FileInfo> getFilesDone(String gid, String location) {
        return getFiles(gid, location, "done");
    }

    /**
     * 그룹 아이디로 완료된 파일 정보를 조회하는 메서드
     * @param gid 그룹 아이디
     * @return 조회된 파일 정보 목록
     */
    default List<FileInfo> getFilesDone(String gid) {
        return getFilesDone(gid, null);
    }

    /**
     * 작업 완료 처리하는 메서드
     * @param gid 그룹 아이디
     */
    default void processDone(String gid) {
        List<FileInfo> items = getFiles(gid);
        items.forEach(item -> {
            item.setDone(true);
        });

        flush();
    }
}
