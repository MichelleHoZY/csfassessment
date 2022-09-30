// Add your models here if you have any



export interface PizzaOrder {
    name: string,
    email: string,
    size: number,
    base: string,
    sauce: string,
    toppings: string[],
    comments: string,
}

export interface OrderSummary {
    name: string,
    email: string,
    orderId: number,
    amount: string
}

export interface OrderSummaryResult {
    list: OrderSummary[]
}