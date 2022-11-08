package db.migration;

import com.czertainly.core.util.V2AttributeMigrationUtils;
import com.czertainly.provider.entity.keystore.enums.JavaMigrationChecksums;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Migration script for the Attributes changes.
 * Prerequisite for the successful migration is to have the AttributeDefinition stored in the database.
 * If the relaxed version of the AttributeDefinition is stored, the migration will fail, including missing
 * type, name, uuid, label.
 */
public class V202211031300__AttributeV2Changes extends BaseJavaMigration {

    private static final String ENTITY_INSTANCE_TABLE_NAME = "entity_instance";

    private static final String ATTRIBUTE_COLUMN_NAME = "attributes";
    private static final String CREDENTIAL_COLUMN_NAME = "credential_data";
    private static final String UNIQUE_IDENTIFIER = "id";

    @Override
    public Integer getChecksum() {
        return JavaMigrationChecksums.V202211031300__AttributeV2Changes.getChecksum();
    }

    public void migrate(Context context) throws Exception {
        try (Statement select = context.getConnection().createStatement()) {
            applyKeyStoreMigration(context);
        }
    }

    private void applyKeyStoreMigration(Context context) throws Exception {
        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery("SELECT id, attributes FROM entity_instance ORDER BY id")) {
                List<String> migrationCommands = V2AttributeMigrationUtils.getMigrationCommands(rows, ENTITY_INSTANCE_TABLE_NAME, ATTRIBUTE_COLUMN_NAME, UNIQUE_IDENTIFIER);
                ResultSet credentialRows = select.executeQuery("SELECT id, credential_data FROM entity_instance ORDER BY id");
                List<String> credentialMigrationCommands = V2AttributeMigrationUtils.getMigrationCommands(credentialRows, ENTITY_INSTANCE_TABLE_NAME, CREDENTIAL_COLUMN_NAME, UNIQUE_IDENTIFIER);
                executeCommands(select, migrationCommands);
                executeCommands(select, credentialMigrationCommands);
            }
        }
    }


    private void executeCommands(Statement select, List<String> commands) throws SQLException {
        for(String command: commands) {
            select.execute(command);
        }
    }
}