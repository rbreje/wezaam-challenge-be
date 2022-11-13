### Technical challenge (for developers)

Assume we have a big legacy system and one of the parts is withdrawal processing (the process that allows to transfer money from company to employee accounts). Now we have chance to completely rewrite the system, including API change (endpoints, DTOs etc). As a techical challenge we suggest you to take it (NOTE: we are interesting to see a new withdrawal processing only). You can do whathever you want following the acceptance criteria:

- Use any architecture you are comfortable with 
- Use modern Java or Kotlin (we use Kotlin for new code)
- Use Spring boot
- Use any database SQL/NoSQL (please use embedded)
- The code must be tested. We don't expect 100% coverage for this challenge, we want to see that you can write sensible tests. For example, if you have several similar converters, no need to test every single class/method, just enough to test one. But for critical logic like withdrawal process we'd like to see coverage of different scenarios
- We expect to see SOLID principles in action
- The service should be easy to run (e.q. docker-compose)

#### Here are some business rules of the withdrawal processing:

- We have a list of users (`/find-all-users` endpoint)
- A user has several payment methods
- A user can execute a withdrawal request using one of his payment methods
- A withdrawal can be executed (sent to a payment provider) as soon as possible or scheduled to be executed later
- After the service receives a request it stores a withdrawal object in our DB and sends a transaction request to a payment provider async. Note: for this task we don't care about a transaction completion  
- We noticed that in current solution we are losing some outgoing events about withdrawals. We MUST 100% notify listeners regarding any withdrawal statuses. That means a new solution should be designed to cover the requirement. For example a withdrawal has been sent to provider, we updated a status to processing in database, and then we have to send a notification. What if the notification was failed to send (e.q. connection issues to a messaging provider)?  

#### Steps to proceed:

- Fork the repository (in case you want to refactor the existing solution). Or you can create the project from scratch
- Implement your nice solution
- If you need to put comments/description regarding the solution please write them in `SOLUTION.md`  
- Once complete invite `makcon` and `pbravowezaam` for review

### Personal TODOs

- Add logging
- Deploy service to docker
- Add security
