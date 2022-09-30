// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

const CreateOrderUrl = "/api/order"

import { HttpClient, HttpClientModule, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { OrderSummary, PizzaOrder } from "./models";

@Injectable()
export class PizzaService {

  constructor(private http: HttpClient) { }

  pizzaOrder!: PizzaOrder
  orderSummary!: OrderSummary

  // POST /api/order
  // Add any required parameters or return type
  createOrder(pizzaOrder: PizzaOrder): Promise<string> { 
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json')

      return firstValueFrom(
        this.http.post<string>(CreateOrderUrl, pizzaOrder, {headers})
      )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string): Promise<OrderSummary[]> { 
    return firstValueFrom(
      this.http.get<OrderSummary[]>('api/order/'+email+'/all')
    )
  }

}
