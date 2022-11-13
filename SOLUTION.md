# Withdrawal Service

![Overview of System Architecture](./images/architecture.png)

## How to Test the API

Start the application and access the following URL
`http://localhost:7070/swagger-ui/`

## Others

I have separated the registering process of all the withdrawals. The end user is going to receive a withdrawal id that
has to be used to retrieve the status later on. The registered withdrawals are executed asynchronously based on
executedAt field. 