package yan0kom.userbal.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yan0kom.userbal.api.dto.*;

import javax.validation.Valid;

@Tag(name = "User API", description = "User related operations")
public interface UserApi {
    @GetMapping("/user/{id}")
    ResponseEntity<UserGetFullOutDto> getById(@PathVariable Long id);

    @GetMapping("/user/search")
    ResponseEntity<Page<UserSearchOutDto>> search(@RequestBody @Valid UserSearchInDto dto);

    @PostMapping("/user/email")
    ResponseEntity<EmailOutDto> createEmail(@RequestBody @Valid EmailCreateInDto inDto);

    @PutMapping("/user/email")
    ResponseEntity<EmailOutDto> updateEmail(@RequestBody @Valid EmailUpdateInDto inDto);

    @DeleteMapping("/user/email")
    ResponseEntity deleteEmail(@RequestBody @Valid EmailDeleteInDto inDto);

    @PostMapping("/user/phone")
    ResponseEntity<PhoneOutDto> createPhone(@RequestBody @Valid PhoneCreateInDto inDto);

    @PutMapping("/user/phone")
    ResponseEntity<PhoneOutDto> updatePhone(@RequestBody @Valid PhoneUpdateInDto inDto);

    @DeleteMapping("/user/phone")
    ResponseEntity deletePhone(@RequestBody @Valid PhoneDeleteInDto inDto);

    @PostMapping("/user/transfer")
    ResponseEntity<TransferOutDto> transfer(@RequestBody @Valid TransferInDto inDto);
}
