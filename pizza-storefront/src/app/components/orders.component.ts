import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderSummary, OrderSummaryResult } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email: string = ''
  orderSummary: OrderSummary = {
    name: '',
    email: '',
    orderId: 0,
    amount: ''
  }
  orderList: OrderSummaryResult = {
    list: []
  }
  ordersList: OrderSummary[] = []

  constructor(private activatedRoute: ActivatedRoute, private pizzaSvc: PizzaService) { }

  ngOnInit(): void {
    // console.log(`${this.activatedRoute.snapshot.params['email']}`)
    this.email = this.activatedRoute.snapshot.params['email']
    this.createOrderList()
  }

  createOrderList() {
    this.pizzaSvc.getOrders(this.email)
      .then(result => {
        console.log(">>> Result: " + result)
        this.orderList = result as unknown as OrderSummaryResult
        console.log("Order list: " + this.orderList.list[0].amount)
        for (let i=0; i<this.orderList.list.length; i++) {
          let orderSummary: OrderSummary = {
            name: '',
            email: '',
            orderId: 0,
            amount: ''
          }
          orderSummary.name = this.orderList.list[i].name
          orderSummary.email = this.orderList.list[i].email
          orderSummary.orderId = this.orderList.list[i].orderId
          orderSummary.amount = this.orderList.list[i].amount
          this.ordersList.push(orderSummary)
        }
      })
      .catch(error => {
        console.log(">>> Error: " + error)
      })
  }

}
