package com.mend.dal.models;

import com.mend.dal.converters.ScanStatusConverter;
import com.mend.dal.converters.ScanTypeConverter;
import com.mend.dal.types.ScanStatus;
import com.mend.dal.types.ScanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scans", indexes = {
        @Index(name = "idx_commit_id", columnList = "commit_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class ScanModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "commit_id")
    private Long commitId;

    @Column(name = "status")
    @ColumnTransformer(read = "cast(status as TEXT)")
    @Convert(converter = ScanStatusConverter.class)
    private ScanStatus scanStatus;

    @Column(name = "type")
    @ColumnTransformer(read = "cast(type as TEXT)")
    @Convert(converter = ScanTypeConverter.class)
    private ScanType scanType;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date updatedAt;

    @Column(name = "issues")
    private Long issues;

    @Column(name = "valid")
    private Boolean valid;
}
