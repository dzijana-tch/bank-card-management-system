package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.TransactionController;
import com.charniuk.bankcardmanagementsystem.dto.request.TransactionFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import com.charniuk.bankcardmanagementsystem.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

  private final TransactionService transactionService;

  @GetMapping
  @Override
  @PreAuthorize("hasRole('ADMIN') or "
      + "@cardServiceImpl.isCardOwner(#transactionFilterRequest.cardId, authentication.principal.user.userId)")
  public ResponseEntity<List<TransactionResponse>> get(
      @RequestBody @Valid TransactionFilterRequest transactionFilterRequest,
      @RequestParam(value = "sort_direction", defaultValue = "ASC") String sortDirection,
      @RequestParam(value = "sort_by", defaultValue = "expirationDate") String sortBy,
      @RequestParam Integer offset,
      @RequestParam Integer limit) {

    Sort sort = sortDirection.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() :
        Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(offset, limit, sort);

    return ResponseEntity.ok(
        transactionService.getAllTransactions(transactionFilterRequest, pageable));
  }

  @PostMapping("/transfer")
  @Override
  @PreAuthorize("@cardServiceImpl.isCardOwner(#transferRequest.senderCardId, authentication.principal.user.userId)")
  public ResponseEntity<Void> makeTransfer(
      @RequestBody @Valid TransferRequest transferRequest) {

    transactionService.makeTransfer(transferRequest);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/withdrawal")
  @Override
  @PreAuthorize("@cardServiceImpl.isCardOwner(#withdrawalRequest.cardId, authentication.principal.user.userId)")
  public ResponseEntity<Void> withdrawFromCard(
      @RequestBody @Valid WithdrawalRequest withdrawalRequest) {

    transactionService.withdrawFromCard(withdrawalRequest);
    return ResponseEntity.ok().build();
  }
}
