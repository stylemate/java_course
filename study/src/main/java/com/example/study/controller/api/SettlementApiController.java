/*
-- -----------------------------------------------------
-- Table `study`.`settlement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `study`.`settlement` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NULL,
  `price` DECIMAL(12,4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_settlement_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `study`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;
 */

package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.Settlement;
import com.example.study.model.network.request.SettlementApiRequest;
import com.example.study.model.network.response.SettlementApiResponse;
import com.example.study.service.SettlementApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/settlement")
public class SettlementApiController extends CrudController<SettlementApiRequest, SettlementApiResponse, Settlement> {

    //CRUD 에서 Read만 하는데 굳이 CrudController를 상속받아야하는걸까?
    //근데 시간이 없어서 걍 구현할 수 있는건 구현해본다
    /*

    Create 하려면 POST method로
    /api/settlement 로
    {
        "transaction_time": "2019-07-31T22:57:56.692",
        "resultCode": "OK",
        "description": "OK",
        "data": {
            "id": 1
        }
    }
    이런식으로 id만 보내면 생성을 하여 `study`.`settlement`에 레코드를 생성한다.
    price를 계산하는 방식은 강의때 구현한 UserApiLogicService안에 있는 userInfo 메소드를 사용하여 모든 orderGroup의 getTotalPrice()를 stream -> map -> reduce 하여 합을 구한다.
    제일 효율적인 방법은 아니겠지만 일단 시간이 없어 재사용하고 봤다.
     */
    @Autowired
    private SettlementApiLogicService settlementApiLogicService;

    @PostConstruct
    public void init() {
        this.baseService = settlementApiLogicService;
    }
}
