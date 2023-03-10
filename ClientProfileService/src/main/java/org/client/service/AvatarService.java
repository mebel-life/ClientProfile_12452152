package org.client.service;

import org.client.dto.AvatarDto;

public interface AvatarService {
    void addAvatarForClient(String icp);

    AvatarDto getAvatarClient(String icp);
}
