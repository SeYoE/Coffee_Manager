package com.tengerine.coffee_manager.repository;

import com.tengerine.coffee_manager.model.Category;
import com.tengerine.coffee_manager.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductJdbcRepository repository;

    // 이렇게 해야지 클래스에 대한 하나의 newProduct 필드만 생성됨.
    // 이게 싫으면 @TestInstance(LifeCycle.PER_CLASS) 설정 필요
    private static Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        repository.insert(newProduct);
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        var product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        var product = repository.findByCategory(newProduct.getCategory());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        repository.update(newProduct);

        var product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("상품을 전체 삭제한다.")
    void testDeleteAll() {
        repository.deleteAll();
        var all = repository.findAll();
        assertThat(all.isEmpty(), is(true));
    }


}