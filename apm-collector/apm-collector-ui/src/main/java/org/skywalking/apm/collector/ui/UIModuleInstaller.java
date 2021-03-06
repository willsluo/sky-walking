package org.skywalking.apm.collector.ui;

import java.util.Iterator;
import java.util.Map;
import org.skywalking.apm.collector.core.client.ClientException;
import org.skywalking.apm.collector.core.framework.CollectorContextHelper;
import org.skywalking.apm.collector.core.framework.DefineException;
import org.skywalking.apm.collector.core.module.ModuleDefine;
import org.skywalking.apm.collector.core.module.ModuleInstaller;
import org.skywalking.apm.collector.core.server.ServerHolder;
import org.skywalking.apm.collector.core.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pengys5
 */
public class UIModuleInstaller implements ModuleInstaller {

    private final Logger logger = LoggerFactory.getLogger(UIModuleInstaller.class);

    @Override public void install(Map<String, Map> moduleConfig,
        Map<String, ModuleDefine> moduleDefineMap, ServerHolder serverHolder) throws DefineException, ClientException {
        logger.info("beginning ui module install");

        UIModuleContext context = new UIModuleContext(UIModuleGroupDefine.GROUP_NAME);
        CollectorContextHelper.INSTANCE.putContext(context);

        Iterator<Map.Entry<String, ModuleDefine>> moduleDefineEntry = moduleDefineMap.entrySet().iterator();
        while (moduleDefineEntry.hasNext()) {
            ModuleDefine moduleDefine = moduleDefineEntry.next().getValue();
            logger.info("module {} initialize", moduleDefine.getClass().getName());
            moduleDefine.initialize((ObjectUtils.isNotEmpty(moduleConfig) && moduleConfig.containsKey(moduleDefine.name())) ? moduleConfig.get(moduleDefine.name()) : null, serverHolder);
        }
    }
}
