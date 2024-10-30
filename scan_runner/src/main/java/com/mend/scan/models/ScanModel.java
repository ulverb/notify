package com.mend.scan.models;


import com.mend.scan.converters.ScanStatusConverter;
import com.mend.scan.converters.ScanTypeConverter;
import com.mend.scan.types.ScanStatus;
import com.mend.scan.types.ScanType;
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
@Table(name = "scans")
public class ScanModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//index
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "branch_name")
    private String branchName;

//index
    @Column(name = "commit_id")
    private Long commitId;

//index
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
