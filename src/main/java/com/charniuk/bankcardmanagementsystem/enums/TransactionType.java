package com.charniuk.bankcardmanagementsystem.enums;

public enum TransactionType {

  // Пополнения
  DEPOSIT,            // Зачисление средств на карту
  REFUND,             // Возврат средств
  CASHBACK,           // Кэшбэк
  TRANSFER_IN,        // Входящий перевод

  // Списания
  PURCHASE,           // Оплата покупки
  WITHDRAWAL,         // Снятие наличных в банкомате
  TRANSFER_OUT,       // Исходящий перевод
  FEE,                // Комиссия (например, за обслуживание)

  // Платежи
  BILL_PAYMENT,       // Оплата счетов (ЖКХ, интернет и т.д.)
  SUBSCRIPTION,       // Автоплатеж (подписки)
  LOAN_PAYMENT        // Погашение кредита
}
