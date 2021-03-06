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

import javax.servlet.http.HttpServletRequest;

import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.InternalServerErrorException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

public interface ServiceProvider {
	
	public List<AddressbookModel> list(
		String query,
		String queryType,
		int position,
		int size
	);

	public AddressbookModel create(
			HttpServletRequest request,
			AddressbookModel addressbook) 
		throws DuplicateException, ValidationException;

	public AddressbookModel read(
			String id) 
		throws NotFoundException;

	public AddressbookModel update(
			HttpServletRequest request,
			String id, 
			AddressbookModel addressbook) 
		throws NotFoundException, ValidationException;

	public void delete(String id) throws NotFoundException, InternalServerErrorException;
	
	public List<ContactModel> listAllContacts(
			String query, 
			String queryType, 
			int position, 
			int size);

	public List<OrgModel> listAllOrgs(
			String query, 
			String queryType, 
			int position, 
			int size);

	/************************* contacts *****************************/
	public abstract List<ContactModel> listContacts(
			String aid,
			String query, 
			String queryType, 
			int position, 
			int size);

	public abstract ContactModel createContact(
			HttpServletRequest request,
			String aid, 
			ContactModel contact)
		throws DuplicateException, ValidationException;
	
	public abstract ContactModel readContact(
			String aid,
			String cid)
		throws NotFoundException;

	public abstract ContactModel updateContact(
			HttpServletRequest request,
			String aid,
			String cid,
			ContactModel contact
	) throws NotFoundException, ValidationException;

	public abstract void deleteContact(
		String aid, 
		String cid
	) throws NotFoundException, InternalServerErrorException;

	/************************* orgs *****************************/
	public abstract List<OrgModel> listOrgs(
			String aid,
			String query, 
			String queryType, 
			int position, 
			int size);

	public abstract OrgModel createOrg(
			HttpServletRequest request,
			String aid, 
			OrgModel org)
		throws DuplicateException, ValidationException;
	
	public abstract OrgModel readOrg(
			String aid,
			String oid)
		throws NotFoundException;

	public abstract OrgModel updateOrg(
			HttpServletRequest request,
			String aid,
			String oid,
			OrgModel org
	) throws NotFoundException, ValidationException;

	public abstract void deleteOrg(
		String aid, 
		String oid
	) throws NotFoundException, InternalServerErrorException;

	/************************* addresses (of contacts) *****************************/
	public abstract List<AddressModel> listAddresses(
			String aid,
			String cid,
			String query, 
			String queryType, 
			int position, 
			int size);

	public abstract AddressModel createAddress(
			HttpServletRequest request,
			String aid, 
			String cid,
			AddressModel address)
		throws DuplicateException, ValidationException;
	
	public abstract AddressModel readAddress(
			String aid,
			String cid,
			String adrid)
		throws NotFoundException;

	public abstract AddressModel updateAddress(
			HttpServletRequest request,
			String aid,
			String cid,
			String adrid,
			AddressModel address
	) throws NotFoundException, ValidationException;

	public abstract void deleteAddress(
		String aid, 
		String cid,
		String adrid
	) throws NotFoundException, InternalServerErrorException;
	
	/************************* addresses (of orgs) *****************************/
	public abstract List<AddressModel> listOrgAddresses(
			String aid,
			String oid,
			String query, 
			String queryType, 
			int position, 
			int size);

	public abstract AddressModel createOrgAddress(
			HttpServletRequest request,
			String aid, 
			String oid,
			AddressModel address)
		throws DuplicateException, ValidationException;
	
	public abstract AddressModel readOrgAddress(
			String aid,
			String oid,
			String adrid)
		throws NotFoundException;

	public abstract AddressModel updateOrgAddress(
			HttpServletRequest request,
			String aid,
			String oid,
			String adrid,
			AddressModel address
	) throws NotFoundException, ValidationException;

	public abstract void deleteOrgAddress(
		String aid, 
		String oid,
		String adrid
	) throws NotFoundException, InternalServerErrorException;
}