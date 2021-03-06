package am.ik.uaa.proxy;

import am.ik.uaa.login.UaaClientToken;
import com.fasterxml.jackson.databind.JsonNode;
import org.cloudfoundry.uaa.UaaClient;
import org.cloudfoundry.uaa.groups.CreateGroupRequest;
import org.cloudfoundry.uaa.groups.DeleteGroupRequest;
import org.cloudfoundry.uaa.groups.ListGroupsRequest;
import org.cloudfoundry.uaa.groups.ListGroupsResponse;
import org.cloudfoundry.uaa.groups.UpdateGroupRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("proxy/groups")
public class GroupsProxyController {
    @GetMapping("")
    public Mono<ListGroupsResponse> groups(Authentication authentication) {
        return ((UaaClientToken) authentication).uaaClient().groups()
            .list(ListGroupsRequest.builder()
                .build());
    }

    @PostMapping("")
    public Mono<?> createGroup(@RequestBody CreateGroupRequest createGroupRequest,Authentication authentication) {
        return ((UaaClientToken) authentication).uaaClient().groups()
            .create(createGroupRequest);
    }

    @PutMapping("{groupId}")
    public Mono<?> updateGroup(@PathVariable String groupId, @RequestBody JsonNode body,Authentication authentication) {
        return ((UaaClientToken) authentication).uaaClient().groups()
            .update(UpdateGroupRequest.builder()
                .groupId(groupId)
                .version(body.get("version").asText())
                .displayName(body.get("displayName").asText())
                .build());
    }

    @DeleteMapping("{groupId}")
    public Mono<?> deleteGroup(@PathVariable String groupId,Authentication authentication) {
        return ((UaaClientToken) authentication).uaaClient().groups()
            .delete(DeleteGroupRequest.builder()
                .groupId(groupId)
                .build());
    }

}
