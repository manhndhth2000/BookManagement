# BookManagement
1. Create fake data for 1000 books

![](./image/1.png)

    url: localhost:8088/book/create
2. Sort list by title

![](image/2.png)

    url: localhost:8088/book/sort
3. Search book by title (keyword)

![](image/9.png)

    url: localhost:8088/book/search/{subtitle}
4. Get list book that has amount = 0

![](image/3.png)

    url: localhost:8088/book/list
5. Buy book

    request: bookId (required), amount

    url: localhost:8088/book/buy

    With valid bookId:
    - example:
![](image/4.png)
    bookId: "py95nc76" - amount: 77

When the number of Books user want to buy smaller than the amount of Books in the inventory then "Success".
![](image/5.png)
the amount of book in the inventory will change by "amount - quantity" and updated date change by the buying time.
![](image/6.png)
Otherwise, send a message to user waiting for ordering new books
![](image/7.png)
And call the event to order more books.
![](image/8.png)
    