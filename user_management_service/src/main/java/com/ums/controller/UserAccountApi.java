package com.ums.controller;

import com.ums.UserManagementServiceApplication;
import com.ums.dto.CustomerVerificationDto;
import com.ums.dto.ErrorMessage;
import com.ums.dto.UserAccountDto;
import com.ums.service.UserAccountService;
import com.ums.utilites.AccountVerificationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_account")
public class UserAccountApi {
    private final UserAccountService userAccountService;

    public UserAccountApi(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @ApiResponses(
            value = {@ApiResponse(responseCode = "500", content = {@Content(mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = Long.class))})}

    )
    @RequestMapping(path = "/save_user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody UserAccountDto userAccountDto) {
        long userid;
        userid = userAccountService.saveUser(userAccountDto);

        return ResponseEntity.ok(String.valueOf(userid));


    }


    @GetMapping("/emailCount/{email}")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "500", content = {@Content(mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = Long.class))})}

    )
    public ResponseEntity<Long> getCountByEmail(@PathVariable String email) {
        long c = userAccountService.countByEmail(email);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/mobileNoCount/{mobileNo}")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "500", content = {@Content(mediaType = "text/plain",
                    schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = Long.class))})}

    )
    public ResponseEntity<Long> getCountByMobileNo(@PathVariable String mobileNo) {
        long c = userAccountService.countByMobile(mobileNo);


        return ResponseEntity.ok(c);

    }

    @PutMapping(value = "/{id}/{verificationOtp}/{verificationType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Verify mobile OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "208", description = "Already Reported", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerVerificationDto.class))})
    })
    public ResponseEntity<CustomerVerificationDto> verifyOtp(@PathVariable long id, @PathVariable String verificationOtp,
                                                             @PathVariable AccountVerificationType verificationType) {

        CustomerVerificationDto dto = userAccountService.verifyOtpAndUpdateAccountStatus(id, verificationOtp, verificationType);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{userAccountId}/verificationStatus",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OTP Verified",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CustomerVerificationDto.class))})
    })
    public CustomerVerificationDto getVerificationStatus(@PathVariable long userAccountId){
        return userAccountService.accountVerificationStatus(userAccountId);
    }


    @GetMapping(value = "/getUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User Account Info",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserAccountDto.class))}),
            @ApiResponse(responseCode = "404",description = "User Account NotFound",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))})
    })
    public UserAccountDto getUserByEmailAddress(@RequestParam String email){

        return userAccountService.getByEmailAddress(email);
    }
}
