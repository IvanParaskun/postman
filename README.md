# Postman
Postman - template driven mailing manager, allowing user to create customized mailing plans.

## Authentication
Postman has the ability to manage multiple kinds of authentication. It provides interfaces, 
using which, developers can create their own authentication flow (e.g. OAuth2, Phone authentication).

### AuthenticationService
AuthenticationService - facade class for security package. It has two simple methods:
1. `create(...)` - creates new `AuthenticationFlow` instance for concrete authentication type.
2. `restore(...)` - restores `AuthenticationFlow` from session by provided state.

### AbstractAuthenticationFlow
AuthenticationFlow controls the process of authentication. Each authentication method consists of next steps:
1. Client obtains AuthorizationServer URL.
2. After following obtained link, client enters credentials and AuthorizationServer redirects it to handler endpoint of
the server.
3. At this part of authentication, application tries to retrieve necessary information. If all information retrieved, it 
creates new account or retrieves one and call `onAuthenticationSuccess()` method of `AuthenticationConsumer`. If
something goes wrong, it calls `onAuthenticationFailure()` method of `AuthenticationConsumer`.

### AuthorizationServer
Developer must provide AuthorizationServer for each authentication method he wants to use. Each one can contain two
endpoints:
1. Endpoint, that contains authentication form.
2. Endpoint, that receives response from first endpoint, restores flow and call its `authenticate()` method.

Server must contain at least one endpoint, that receives response. If developer using OAuth2 method, it doesn't need to
provide special form, instead of this, he should redirect client to Consent Screen of OAuth2 provider.

### AuthenticationSession
When client creates new AuthenticationFlow, application persists its current state with fields, provided by client. When
`AuthorizationServer` needs to process authentication data, it restores AuthenticationFlow using that session.

`AuthenticationSession` has 5 minutes time to live property. It means, that client has 5 minutes to authenticate, before
the session is deleted. If client tries to authenticate after session expiration time, `onAuthenticationFailure()`
method will be called with `SessionExpiredException` as property value.

### AuthenticationConsumer
Client oriented part of application (e.g. Telegram Bot, WebApp client endpoint) can implement `AuthenticationConsumer`
and be used as result endpoint for authentication flow.

### Credential
Each account depends on some credential type, which presents one authentication method. Credential interface provides
one method `getUniqueField()`, which returns unique field of credential (e.g. email for OAuth2, phone number for
Phone authentication).

