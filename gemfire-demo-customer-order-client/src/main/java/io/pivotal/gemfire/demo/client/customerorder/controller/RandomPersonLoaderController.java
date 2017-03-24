package io.pivotal.gemfire.demo.client.customerorder.controller;

import io.pivotal.gemfire.demo.client.customerorder.util.RandomPersonBuilderUtils;
import io.pivotal.gemfire.demo.model.gf.key.PersonKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Person;
import org.apache.geode.cache.Region;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@RestController
public class RandomPersonLoaderController {

    @Resource(name = "person")
    private Region<PersonKey, Person> personRegion;

    @RequestMapping(value = "/loadRandomPeople", method = RequestMethod.POST)
    public void loadRandomPersons(@RequestParam(value = "num") int num) {
        RandomPersonBuilderUtils randomPersonBuilderUtils = new RandomPersonBuilderUtils();
        //randomPersonBuilderUtils.clearPersonData(personRegion);
        int baselineCount = personRegion.keySetOnServer().size();
        Map personList = randomPersonBuilderUtils.buildPerson(num, baselineCount);
        personRegion.putAll(personList);
    }

    @RequestMapping(value = "/clearPersonData", method = RequestMethod.POST)
    public void clearPersonData() {
        RandomPersonBuilderUtils randomPersonBuilderUtils = new RandomPersonBuilderUtils();
        randomPersonBuilderUtils.clearPersonData(personRegion);
    }
}
