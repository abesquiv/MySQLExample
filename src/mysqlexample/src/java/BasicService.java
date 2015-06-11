package mysqlexample.src.java;

import java.security.acl.Owner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import mysqlexample.src.java.MySQLAccess.TableType;

import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/basic")
public class BasicService {
  @GET
  @Path("/machine/owner/{owner}")
  @Produces("application/json")
  public Response machineOwner(@PathParam("owner") String owner) throws JSONException {
    return owner(owner, TableType.MACHINE);
  }
  
  @GET
  @Path("/userset/owner/{owner}")
  @Produces("application/json")
  public Response usersetOwner(@PathParam("owner") String owner) throws JSONException {
    return owner(owner, TableType.USERSET);
  }
  
  public Response owner(String owner, TableType type){
    MySQLAccess dios = new MySQLAccess();
    ResultSet resultSet = dios.getEntriesForOwner(owner, type);
    String result;
    try {
      result = MySQLAccess.getString(resultSet);
    } catch (Exception e) {
      result = e.getMessage();
    }
    return Response.status(200).entity(result).build();
  }
}
