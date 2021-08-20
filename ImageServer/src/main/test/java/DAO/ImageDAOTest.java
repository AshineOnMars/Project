package DAO;

import org.junit.Test;

import java.util.List;

public class ImageDAOTest {

    @Test
    public void insert() {
        ImageDAO imageDAO=new ImageDAO();
        Image image=new Image();
        image.setImageName("NYD.jpg");
        image.setSize(13329);
        image.setPath("./image/134fa5e3b69b8fad49dda960d9c7c7ad");
        image.setUploadTime("20220820");
        image.setContentType("image/jpeg");
        image.setMd5("134fa5e3b69b8fad49dda960d9c7c7ad");
        imageDAO.insert(image);
    }

    @Test
    public void selectAll() {
        ImageDAO imageDAO=new ImageDAO();
        List<Image> list=imageDAO.selectAll();
        System.out.println(list.size());
    }

    @Test
    public void selectOne() {
        ImageDAO imageDAO=new ImageDAO();
//        Image image=imageDAO.selectOne(33);
        Image image=imageDAO.selectOne(19);
        System.out.println(image);
    }

    @Test
    public void delete() {
        ImageDAO imageDAO=new ImageDAO();
        imageDAO.delete(53);
    }

    @Test
    public void selectByMd5() {
        ImageDAO imageDAO=new ImageDAO();
//        Image image=imageDAO.selectByMd5("1111");
        Image image=imageDAO.selectByMd5("601767a21bc5e386ab304f6e02d0c3af");
        System.out.println(image);
    }
}