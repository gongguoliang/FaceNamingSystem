package com.gong.FaceNamingSystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaceNamingSystemApplicationTests {
	/*@Autowired
	private CMseetaFaceDao cmseetafaceDao;
	@Autowired
	private UserDao userDao;*/
	@Test
	public void contextLoads() {

	}
	/*@Test
	public void test1() throws Exception {
		UserEntity user=new UserEntity();;
		user.setUserName("gong");
		user.setCollectionName(user.getUserID()+"face");
		userDao.insert(user);
		CMseetaFaceEntiy face=new CMseetaFaceEntiy();
		float[] feature = {1,2,3,4,5,6};
		face.setFeatures(feature);
		cmseetafaceDao.insert(face,"201494086face");
	}
*/
}
