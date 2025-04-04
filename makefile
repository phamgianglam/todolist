# Makefile

# Load environment variables from .env file
#!make
include .env

# Default target
.PHONY: update rollback
update:
	@liquibase \
		--url=$(DB_URL) \
		--username=$(DB_USERNAME) \
		--password=$(DB_PASSWORD) \
		--search-path=$(LIQUIBASE_SEARCH_PATH) \
		--changeLogFile=$(CHANGELOG_FILE) \
		update

rollback:
	@liquibase \
		--url=$(DB_URL) \
		--username=$(DB_USERNAME) \
		--password=$(DB_PASSWORD) \
		--search-path=$(LIQUIBASE_SEARCH_PATH) \
		--changeLogFile=$(CHANGELOG_FILE) \
		rollbackCount $(COUNT)

COUNT ?= 1