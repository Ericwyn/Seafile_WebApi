# Seafile_WebAPI
Seafile webapi implemented by java,Base on the Seafile Web API V2.1

## Dependency
 - fastjson-1.2.36
 - okhttp-3.8.1
    - okio-1.13.0

## Examples

    String SERVICE_URL="https://cloud.seafile.com";
    OkHttpClient client=new OkHttpClient();
    SeafileApi api=new SeafileApi(SERVICE_URL);
    String token = api.obtainAuthToken(client, Account.username,Account.password);
    System.out.println("token = " + token);

## Complete documentation
You can see the Complete documentation in [Seafile-WebAPI-V2.1](https://manual.seafile.com/develop/web_api_v2.1.html)