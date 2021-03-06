/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.addressbooks;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.opentdc.service.GenericService;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.InternalServerErrorException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

/**
 * Describes the REST service API of addressbooks, contacts, orgs and addresses.
 * @author bruno
 *
 */
@Path("/api/addressbooks")
public class AddressbooksService extends GenericService<ServiceProvider> {

	private static final Logger logger = Logger.getLogger(AddressbooksService.class.getName());
	private ServiceProvider sp = null;
	
	/**
	 * Invoked for each service invocation (Constructor)
	 */
	public AddressbooksService(
		@Context ServletContext context
	) throws ReflectiveOperationException{
		logger.info("> AddressbooksService()");
		if (sp == null) {
			sp = this.getServiceProvider(AddressbooksService.class, context);
		}
		logger.info("AddressbooksService() initialized");
	}

	/**
	 * Return a list of addressbooks
	 * @param query
	 * @param queryType
	 * @param position	the position to start the result set with (default: GenericService.DEF_POSITION)
	 * @param size	the number of addressbook objects to return (default: GenericService.DEF_SIZE)
	 * @return	a list of size AddressbookModels starting from position 
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AddressbookModel> list(
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size			
	) {
		return sp.list(query, queryType, position, size);
	}

	/**
	 * Create a new addressbook object
	 * @param addressbook
	 * @return	the newly created addressbook; this is the same as the parameter plus a given random id
	 * @throws DuplicateException if an addressbook with the same id already exists
	 * @throws ValidationException if any validation checks fail
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressbookModel create(
		@Context HttpServletRequest request,
		AddressbookModel addressbook
	) throws DuplicateException, ValidationException {
		return sp.create(request, addressbook);
	}

	/**
	 * Return the addressbook with id
	 * @param id   the id to look for
	 * @return  the addressbook with id
	 * @throws NotFoundException	if no addressbook with this id exists
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressbookModel read(
		@PathParam("id") String id
	) throws NotFoundException {
		return sp.read(id);
	}

	/**
	 * Change some attributes of the addressbook object
	 * @param id		the id of the addressbook object to change
	 * @param addressbook	an addressbook object with the new attributes. BEWARE: all attributes will be changed to these values.
	 * @return	the addressbook object with the updated attributes
	 * @throws NotFoundException	if no addressbook with such an id exists
	 * @throws ValidationException	if any validation checks on the attribute values failed; see log for reason
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressbookModel update(
		@Context HttpServletRequest request,
		@PathParam("id") String id,
		AddressbookModel addressbook
	) throws NotFoundException, ValidationException {
		return sp.update(request, id, addressbook);
	}

	@DELETE
	@Path("/{id}")
	public void delete(
		@PathParam("id") String id
	) throws NotFoundException, InternalServerErrorException {
		sp.delete(id);
	}
	
	@GET
	@Path("/allContacts")
	public List<ContactModel> allContacts(
			@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
			@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
			@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
			@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listAllContacts(query, queryType, position, size);
	}

	@GET
	@Path("/allOrgs")
	public List<OrgModel> allOrgs(
			@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
			@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
			@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
			@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listAllOrgs(query, queryType, position, size);
	}

	/********************************** contact ***************************************/
	@GET
	@Path("/{aid}/contact")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContactModel> listContacts(
		@PathParam("aid") String aid,
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listContacts(aid, query, queryType, position, size);
	}
	
	@POST
	@Path("/{aid}/contact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ContactModel createContact(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid, 
		ContactModel contact
	) throws DuplicateException, ValidationException {
		return sp.createContact(request, aid, contact);
	}
	
	@GET
	@Path("/{aid}/contact/{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContactModel readContact(
		@PathParam("aid") String aid,
		@PathParam("cid") String cid
	) throws NotFoundException {
		return sp.readContact(aid, cid);
	}

	@PUT
	@Path("/{aid}/contact/{cid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ContactModel updateContact(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid,
		@PathParam("cid") String cid,
		ContactModel contact
	) throws NotFoundException, ValidationException {
		return sp.updateContact(request, aid, cid, contact);
	}

	@DELETE
	@Path("/{aid}/contact/{cid}")
	public void deleteContact(
		@PathParam("aid") String aid,
		@PathParam("cid") String cid
	) throws NotFoundException, InternalServerErrorException {
		sp.deleteContact(aid, cid);
	}
	
	/********************************** orgs ***************************************/
	@GET
	@Path("/{aid}/org")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrgModel> listOrgs(
		@PathParam("aid") String aid,
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listOrgs(aid, query, queryType, position, size);
	}

	@POST
	@Path("/{aid}/org")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrgModel createOrg(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid, 
		OrgModel org
	) throws DuplicateException, ValidationException {
		return sp.createOrg(request, aid, org);
	}
	
	@GET
	@Path("/{aid}/org/{oid}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrgModel readOrg(
		@PathParam("aid") String aid,
		@PathParam("oid") String oid
	) throws NotFoundException {
		return sp.readOrg(aid, oid);
	}

	@PUT
	@Path("/{aid}/org/{oid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OrgModel updateOrg(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid,
		@PathParam("oid") String oid,
		OrgModel org
	) throws NotFoundException, ValidationException {
		return sp.updateOrg(request, aid, oid, org);
	}

	@DELETE
	@Path("/{aid}/org/{oid}")
	public void deleteOrg(
		@PathParam("aid") String aid,
		@PathParam("oid") String oid
	) throws NotFoundException, InternalServerErrorException {
		sp.deleteOrg(aid, oid);
	}

	/********************************** address (of contact) ***************************************/
	@GET
	@Path("/{aid}/contact/{cid}/address")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AddressModel> listAddresses(
		@PathParam("aid") String aid,
		@PathParam("cid") String cid,
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listAddresses(aid, cid, query, queryType, position, size);
	}

	@POST
	@Path("/{aid}/contact/{cid}/address")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel createAddress(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid, 
		@PathParam("cid") String cid,
		AddressModel address
	) throws DuplicateException, ValidationException {
		return sp.createAddress(request, aid, cid, address);
	}
	
	@GET
	@Path("/{aid}/contact/{cid}/address/{adrid}")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel readAddress(
		@PathParam("aid") String aid,
		@PathParam("cid") String cid,
		@PathParam("adrid") String adrid
	) throws NotFoundException {
		return sp.readAddress(aid, cid, adrid);
	}

	@PUT
	@Path("/{aid}/contact/{cid}/address/{adrid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel updateAddress(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid,
		@PathParam("cid") String cid,
		@PathParam("adrid") String adrid,
		AddressModel address
	) throws NotFoundException, ValidationException {
		return sp.updateAddress(request, aid, cid, adrid, address);
	}

	@DELETE
	@Path("/{aid}/contact/{cid}/address/{adrid}")
	public void deleteAddress(
		@PathParam("aid") String aid,
		@PathParam("cid") String cid,
		@PathParam("adrid") String adrid
	) throws NotFoundException, InternalServerErrorException {
		sp.deleteAddress(aid, cid, adrid);
	}
	
	/********************************** address (of org) ***************************************/
	@GET
	@Path("/{aid}/org/{oid}/address")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AddressModel> listOrgAddresses(
		@PathParam("aid") String aid,
		@PathParam("oid") String oid,
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.listOrgAddresses(aid, oid, query, queryType, position, size);
	}

	@POST
	@Path("/{aid}/org/{oid}/address")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel createOrgAddress(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid, 
		@PathParam("oid") String oid,
		AddressModel address
	) throws DuplicateException, ValidationException {
		return sp.createOrgAddress(request, aid, oid, address);
	}
	
	@GET
	@Path("/{aid}/org/{oid}/address/{adrid}")
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel readOrgAddress(
		@PathParam("aid") String aid,
		@PathParam("oid") String oid,
		@PathParam("adrid") String adrid
	) throws NotFoundException {
		return sp.readOrgAddress(aid, oid, adrid);
	}

	@PUT
	@Path("/{aid}/org/{oid}/address/{adrid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AddressModel updateOrgAddress(
		@Context HttpServletRequest request,
		@PathParam("aid") String aid,
		@PathParam("oid") String oid,
		@PathParam("adrid") String adrid,
		AddressModel address
	) throws NotFoundException, ValidationException {
		return sp.updateOrgAddress(request, aid, oid, adrid, address);
	}

	@DELETE
	@Path("/{aid}/org/{oid}/address/{adrid}")
	public void deleteOrgAddress(
		@PathParam("aid") String aid,
		@PathParam("oid") String oid,
		@PathParam("adrid") String adrid
	) throws NotFoundException, InternalServerErrorException {
		sp.deleteOrgAddress(aid, oid, adrid);
	}
}
