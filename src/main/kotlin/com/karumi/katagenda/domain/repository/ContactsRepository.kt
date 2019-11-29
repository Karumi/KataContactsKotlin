package com.karumi.katagenda.domain.repository

import com.karumi.katagenda.common.repository.DataSource
import com.karumi.katagenda.common.repository.Repository
import com.karumi.katagenda.domain.Contact

class ContactsRepository(dataSource: DataSource<Contact>) : Repository<Contact>(dataSource)
