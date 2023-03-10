package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.dto.AvatarDto;
import org.client.dto.IndividualDto;
import org.client.service.AvatarService;
import org.client.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
@AllArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private final IndividualService individualService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addAvatarForClient(String icp) {
        String uuid = individualService.getClient(icp).getUuid();
        if(individualService.getClient(icp).getAvatar() == null) {
            ResponseEntity<String> responseEntity =  restTemplate.postForEntity("http://localhost:8888/avatar", uuid, String.class);
            HttpStatus statusCode = responseEntity.getStatusCode();

            //как сделать чтобы в течении какого то времени проверять статус ответа??

            //почему AvatarDto красным подчеркивает??((
            //получаю ответ от Аватара, засетил поле, а в лоудер отправляю сообщение из Аватара, или отсюда нужно?
            if (statusCode == HttpStatus.OK) {
                IndividualDto dto = individualService.getClient(icp);
                ResponseEntity<AvatarDto> getAvatar =  restTemplate.getForEntity("http://localhost:8888/avatar", AvatarDto, AvatarDto.class);
                dto.setAvatar(Collections.singleton(getAvatar.getBody()));
            }

        } else {
            System.out.println("Client have avatar");
        }
    }

    @Override
    public AvatarDto getAvatarClient(String icp) {
        return (AvatarDto) Collections.singleton(individualService.getClient(icp).getAvatar());
    }
}
