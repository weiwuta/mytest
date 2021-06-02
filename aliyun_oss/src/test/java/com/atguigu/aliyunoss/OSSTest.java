package com.atguigu.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.junit.After;
import org.junit.Test;

public class OSSTest {
    String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
    String accessKeyId = "LTAI4GEBk2jZKvKR7jiS4a8f";
    String accessKeySecret = "6ORITLYSSh4p31VXCargWjv2JvFwJe";
//    String bucketName = "guli-file-2020-wwt"; //这个是已经存在的，所以应该创建新的
    String bucketName = "guli-file-2020-wwt-new";
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    /**
     * 创建bucket
     */
    @Test
    public void testCreateBucket() {
        // 创建存储空间。
        ossClient.createBucket(bucketName);
    }

    /**
     * 判断桶是否存在
     */
    @Test
    public void testExist() {
        boolean exists = ossClient.doesBucketExist(bucketName);
        System.out.println(exists);
    }

    @After
    public void after(){
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void testAccessControl() {
        // 设置存储空间的访问权限为：公共读。
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }
}
