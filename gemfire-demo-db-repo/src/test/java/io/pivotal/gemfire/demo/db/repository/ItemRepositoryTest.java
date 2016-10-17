package io.pivotal.gemfire.demo.db.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.gemfire.demo.db.CustomerOrderDBApplication;
import io.pivotal.gemfire.demo.model.orm.ItemEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { CustomerOrderDBApplication.class})
@Transactional
@Rollback
public class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@Before
	public void setUp() throws Exception {
		ItemEntity pencil = new ItemEntity("pencil");
		pencil.setName("pencil");
		pencil.setDescription("pencil decription");
		pencil.setPrice(new BigDecimal("0.99"));
		itemRepository.save(pencil);

		ItemEntity pen = new ItemEntity("pen");
		pen.setName("pen");
		pen.setDescription("pen description");
		pen.setPrice(new BigDecimal("1.49"));
		itemRepository.save(pen);

		ItemEntity paper = new ItemEntity("paper");
		paper.setName("pen");
		paper.setDescription("paper description");
		paper.setPrice(new BigDecimal("0.10"));
		itemRepository.save(paper);
	}

	@Test
	public void testCount() {
		Assert.assertEquals(3, itemRepository.count());
	}

	@Test
	public void testFindByName() {
		Set<ItemEntity> itemSet = itemRepository.findByName("book");
		Assert.assertEquals(0, itemSet.size());

		itemSet = itemRepository.findByName("pencil");
		Assert.assertEquals(1, itemSet.size());
	}

}
