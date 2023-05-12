package yan0kom.userbal.api.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import yan0kom.userbal.api.UserApi;
import yan0kom.userbal.api.dto.*;
import yan0kom.userbal.domain.UserFilter;
import yan0kom.userbal.domain.entity.UserHead;
import yan0kom.userbal.domain.service.UserService;

import java.util.function.Function;

@RequiredArgsConstructor
@RestController
public class UserApiImpl implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<UserGetFullOutDto> getById(Long id) {
        var user = userService.getById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(UserGetFullOutDto.from(user));
    }

    @Override
    public ResponseEntity<Page<UserSearchOutDto>> search(UserSearchInDto inDto) {
        Function<UserHead, UserSearchOutDto> converter = (userHead) -> {
            var outDto = new UserSearchOutDto();
            outDto.setId(userHead.getId());
            outDto.setName(userHead.getName());
            outDto.setDateOfBirth(userHead.getDateOfBirth());
            return outDto;
        };
        var userFilter = new UserFilter(
                inDto.getDateOfBirthAfter(), inDto.getPhone(), inDto.getNamePrefix(), inDto.getEmail());
        var pageRequest = PageRequest.of(
                inDto.getPageNumber(), inDto.getPageSize(), Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(userService.search(userFilter, pageRequest).map(converter));
    }

    @Override
    public ResponseEntity<EmailOutDto> createEmail(EmailCreateInDto inDto) {
        var email = userService.addEmail(inDto.getEmail());
        return ResponseEntity.ok(EmailOutDto.from(email));
    }

    @Override
    public ResponseEntity<EmailOutDto> updateEmail(EmailUpdateInDto inDto) {
        var email = userService.updateEmail(inDto.getId(), inDto.getEmail());
        return ResponseEntity.ok(EmailOutDto.from(email));
    }

    @Override
    public ResponseEntity deleteEmail(EmailDeleteInDto inDto) {
        userService.deleteEmail(inDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<PhoneOutDto> createPhone(PhoneCreateInDto inDto) {
        var phone = userService.addPhone(inDto.getPhone());
        return ResponseEntity.ok(PhoneOutDto.from(phone));
    }

    @Override
    public ResponseEntity<PhoneOutDto> updatePhone(PhoneUpdateInDto inDto) {
        var phone = userService.updatePhone(inDto.getId(), inDto.getPhone());
        return ResponseEntity.ok(PhoneOutDto.from(phone));
    }

    @Override
    public ResponseEntity deletePhone(PhoneDeleteInDto inDto) {
        userService.deletePhone(inDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<TransferOutDto> transfer(TransferInDto inDto) {
        var balance = userService.transfer(inDto.getPayeeId(), inDto.getValue());
        return ResponseEntity.ok(TransferOutDto.from(balance));
    }
}
