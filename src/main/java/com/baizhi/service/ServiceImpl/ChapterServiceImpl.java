package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.UUIDUtil;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public HashMap<String, Object> queryByPage(String albumId, Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //总条数
        Integer records = chapterDao.queryRecords();
        map.put("records",records);

        //总页数
        Integer total = records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //当前页
        map.put("page",page);

        //数据
        List<Chapter> albums = chapterDao.queryByPage(albumId,(page - 1) * rows, rows);
        map.put("rows",albums);

        return map;
    }

    @Override
    public String add(Chapter chapter) {
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        chapter.setUp_date(new Date());
        System.out.println("=调用Dao方法执行添加   数据入库= "+chapter);
        chapterDao.save(chapter);
        return uuid;
    }

    @Override
    public void del(Chapter chapter) {
        chapterDao.delete(chapter);
    }

    @Override
    public HashMap<String, Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            //获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            //获取文件名
            String filename = url.getOriginalFilename();
            String name=new Date().getTime()+"-"+filename;
            //文件上传
            url.transferTo(new File(realPath,name));
            //文件的大小   字节  Byte  b  KB MB
            double size = (double) url.getSize();
            double dd= size/1024/1024;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String sizes = decimalFormat.format(dd)+"MB";
            //获取文件时长
            AudioFile audioFile = AudioFileIO.read(new File(realPath, name));

            //获取时长 秒
            int length = audioFile.getAudioHeader().getTrackLength();

            String duration=length/60+"分"+length%60+"秒";

            //修改数据
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(name);
            chapter.setSize(sizes);
            chapter.setDuration(duration);

            System.out.println("==调用Dao执行修改操作=="+chapter);
            chapterDao.update(chapter);

            map.put("success","200");
            map.put("message","上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success","400");
            map.put("message","上传失败");
        }

        return map;
    }
    @Override
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response) {

        try {

            //1.获取文件的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");

            //2.根据路径获取文件
            FileInputStream inputStream = new FileInputStream(new File(realPath,fileName));

            //3.设置响应格式   响应头,文件名   attachment:以附件的形式下载   inline:在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));

            //获取输出流
            ServletOutputStream outputStream = response.getOutputStream();

            //4.文件下载
            IOUtils.copy(inputStream,outputStream);

            //关闭资源
            //inputStream.close();
            //outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
