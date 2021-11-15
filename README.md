# BookManagement
1. Create fake data for 1000 books

    ![](image/Screenshot from 2021-11-15 10-49-21.png)

    url: localhost:8088/book/create
2. Sort list by title

    ![](image/Screenshot from 2021-11-15 10-49-54.png)

    url: localhost:8088/book/sort
3. Search book by title(keyword)

    ![](image/Screenshot from 2021-11-15 11-35-07.png)

    url: localhost:8088/book/search/{subtitle}
4. Get list book that has amount = 0

    ![](image/Screenshot from 2021-11-15 10-50-12.png)

    url: localhost:8088/book/list
5. Buy book
    request: bookId (required), amount
    url: localhost:8088/book/buy
    With valid bookId:
    - example:

    ![](image/Screenshot from 2021-11-15 10-50-33.png)
   
    bookId: "py95nc76" - amount: 77

    When the number of Books user want to buy smaller than the amount of Books in the inventory then "Success".

    ![](image/Screenshot from 2021-11-15 10-51-00.png)

    the amount of book in the inventory will change by "amount - quantity" and updated date change by the buying time.

    ![](image/Screenshot from 2021-11-15 10-51-09.png)

    Otherwise, send a message to user waiting for ordering new books

    ![](image/Screenshot from 2021-11-15 10-51-24.png)

    And call the event to order more books.

    ![](image/Screenshot from 2021-11-15 10-51-29.png)
    