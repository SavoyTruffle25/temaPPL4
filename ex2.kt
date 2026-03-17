package org.example

import java.util.Date

interface PaymentMethod
{
   fun pay(fee: Double) : Boolean
}

class CashPayment(private var availableAmount: Double) : PaymentMethod{
    override fun pay(fee: Double) : Boolean
    {
        if(fee <= availableAmount)
        {
            availableAmount -= fee
            println("Transzactie cash reusita")
            return true
        }
        return false
    }
}

class BankAccount(
    var availableAmount: Double,
    val cardNumber: String,
    val expirationDate: Date,
    val cvvCode: Int,
    val userName: String)
{
    fun updateAmount(value: Double): Boolean {
        if (availableAmount >= value) {
            availableAmount -= value
            return true
        }
        return false
    }
}

class CardPayment(private var bankAccount: BankAccount) : PaymentMethod{
    override fun pay(fee: Double): Boolean {
        if(bankAccount.updateAmount(fee))
        {
            println("Transzactie card reusita")
            return true
        }
        return false
    }
}

class Ticket(val movie: String, val price: Double) {
    fun printTicket() {
        println("Bilet pentru filmul: $movie la pretul de $price RON\n")
    }
}

class CinemaBookingService(private val paymentMethod: PaymentMethod){
    fun bookTicket(movie: String, price: Double) : Ticket?{
        val isPaymentSuccessful = paymentMethod.pay(price)
        if (isPaymentSuccessful) {
            println("Bilet cumparat")
            return Ticket(movie, price)
        } else {
            println("Achiziția biletului a eșuat din cauza plății.")
        }
        return null
    }
}

fun main() {
    // varianta cash
    val cashPayment = CashPayment(50.0)
    val PlataCash = CinemaBookingService(cashPayment)
    val bilet =PlataCash.bookTicket("Shrek 2", 13.4)
    bilet?.printTicket()

    // varianta cu card
    val cont= BankAccount(50.0,"290329038208", Date(), 123, "Gigel Popescu")
    val cardPayment = CardPayment(cont)
    val PlataCard = CinemaBookingService(cardPayment)
    val bilet1 = PlataCard.bookTicket("Barbie", 20.0)
    bilet1?.printTicket()
}