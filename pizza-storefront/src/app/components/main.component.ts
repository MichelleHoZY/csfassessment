import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AbstractControl, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { OrderSummary, PizzaOrder } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  form!: FormGroup
  formArr!: FormArray
  pizzaOrder: PizzaOrder = {
    name: '',
    email: '',
    size: 0,
    base: '',
    sauce: '',
    toppings: [],
    comments: '',
  }
  oneCheckbox: number = 0

  pizzaSize = SIZES[0]
  pizzaToppings = PizzaToppings

  constructor(private fb: FormBuilder, private router: Router, private pizzaSvc: PizzaService) {}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  addToppingsArray() {
    const arrayData = this.fb.group({
      chicken: false,
      seafood: false,
      beef: false,
      vegetables: false,
      cheese: false,
      arugula: false,
      pineapple: false
    })
    this.formArr.push(arrayData)
    console.log(arrayData.value)
  }

  // toppingsClick(i: number) {
  //   let count: number = 0
  //   count = i + count
  //   console.log("Count: " + count)
  //   this.oneCheckbox = count
  // }

//   ageRangeValidator(num: number): ValidatorFn {
//     return (control: AbstractControl): { [key: string]: boolean } | null => {
//       console.log(control.value)
//       for (let i = 0; i < this.pizzaToppings.length; i++) {
//         console.log("Control value: " + control.value[i])
//         if (control.value[i] === true){
//           return { 'toppings': true}
//         }
//       }
//         // if (this.oneCheckbox > 0) {
//         //     return { 'toppings': true };
//         // }
//         return null;
//     };
// }

  // atLeastOneTopping(): ValidatorFn {
  //   return function validate(FormGroup: FormGroup) {
  //     if (this.toppingsClick() > 0) {
  //       return requireOneCheckbox: true
  //     }
  //     return null
  //   }
  // }


  createForm(): FormGroup {
    this.formArr = this.fb.array([])
    this.addToppingsArray()
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      pizzaSize: [0, Validators.required],
      base: ['', Validators.required],
      toppings: this.formArr,
      sauce: this.fb.control<string>('classic', [Validators.required]),
      comments: this.fb.control<string>('')
    },)
  }

  processForm() {
    console.log(">>> Form data: " + JSON.stringify(this.form.value))
    this.pizzaOrder = this.form.value as PizzaOrder
    console.log(">>> Toppings list: " + JSON.stringify(this.pizzaOrder))
    console.log(">>> Email: " + this.pizzaOrder.email)
    this.router.navigate(['orders/' + this.pizzaOrder.email])
    this.pizzaSvc.pizzaOrder = this.pizzaOrder
    this.pizzaSvc.createOrder(this.pizzaOrder)
  }

  clicked(i: number) {
    console.log(">>> Clicked index: " + i)
  }

  getOrders(email: string) {
    console.log(">>> Get orders pressed")
    this.pizzaSvc.getOrders(email)
      .then(result => {
        console.log(">>> Get orders: " + JSON.stringify(result))
      })
      .catch(error => {
        console.log(">>> Error: " + JSON.stringify(error))
      })
  }



}
