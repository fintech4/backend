//package com.Toou.Toou.usecase;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.Toou.Toou.domain.model.AccountAsset;
//import com.Toou.Toou.domain.model.HoldingIndividualStock;
//import com.Toou.Toou.port.out.AccountAssetPort;
//import com.Toou.Toou.port.out.HoldingStockPort;
//import com.Toou.Toou.port.out.StockHistoryPort;
//import com.Toou.Toou.port.out.StockMetadataPort;
//import java.time.LocalDate;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//class AccountHoldingUseCaseTest {
//
//	private final AccountAssetPort accountAssetPort = mock(AccountAssetPort.class);
//	private final HoldingStockPort holdingStockPort = mock(HoldingStockPort.class);
//	private final StockHistoryPort stockHistoryPort = mock(StockHistoryPort.class);
//	private final StockMetadataPort stockMetadataPort = mock(StockMetadataPort.class);
//	private final AccountHoldingUseCase accountHoldingUseCase = new AccountHoldingService(
//			accountAssetPort, holdingStockPort, stockHistoryPort, stockMetadataPort);
//
//	@Test
//	void execute() {
//		// Given
//		String
//		2024-08-19T02:50:45.342Z  WARN 1 --- [toou] [io-8080-exec-10] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: S1009
//		2024-08-19T02:50:45.342Z ERROR 1 --- [toou] [io-8080-exec-10] o.h.engine.jdbc.spi.SqlExceptionHelper   : '∞' is not a valid numeric or approximate numeric value
//		2024-08-19T02:50:45.345Z ERROR 1 --- [toou] [io-8080-exec-10] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.orm.jpa.JpaSystemException: Unable to bind parameter #2 - Infinity ['∞' is not a valid numeric or approximate numeric value] [n/a]] with root cause
//
//		com.mysql.cj.exceptions.WrongArgumentException: '∞' is not a valid numeric or approximate numeric value
//		at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:na]
//		at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77) ~[na:na]
//		at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:na]
//		at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499) ~[na:na]
//		at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480) ~[na:na]
//		at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:61) ~[mysql-connector-j-8.3.0.jar!/:8.3.0]
//		at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:85) ~[mysql-connector-j-8.3.0.jar!/:8.3.0]
//		at com.mysql.cj.NativeQueryBindings.setDouble(NativeQueryBindings.java:358) ~[mysql-connector-j-8.3.0.jar!/:8.3.0]
//		at com.mysql.cj.jdbc.ClientPreparedStatement.setDouble(ClientPreparedStatement.java:1517) ~[mysql-connector-j-8.3.0.jar!/:8.3.0]
//		at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.setDouble(HikariProxyPreparedStatement.java) ~[HikariCP-5.1.0.jar!/:na]
//		at org.hibernate.type.descriptor.jdbc.DoubleJdbcType$1.doBind(DoubleJdbcType.java:89) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.type.descriptor.jdbc.BasicBinder.bind(BasicBinder.java:61) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.mutation.internal.JdbcValueBindingsImpl.lambda$beforeStatement$0(JdbcValueBindingsImpl.java:87) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at java.base/java.lang.Iterable.forEach(Iterable.java:75) ~[na:na]
//		at org.hibernate.engine.jdbc.mutation.spi.BindingGroup.forEachBinding(BindingGroup.java:51) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.mutation.internal.JdbcValueBindingsImpl.beforeStatement(JdbcValueBindingsImpl.java:85) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:130) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:55) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:55) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.doStaticUpdate(UpdateCoordinatorStandard.java:781) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.performUpdate(UpdateCoordinatorStandard.java:328) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.persister.entity.mutation.UpdateCoordinatorStandard.update(UpdateCoordinatorStandard.java:245) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.action.internal.EntityUpdateAction.execute(EntityUpdateAction.java:169) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:632) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:499) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:371) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:41) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1425) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:487) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2324) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1981) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101) ~[hibernate-core-6.5.2.Final.jar!/:6.5.2.Final]
//		at org.springframework.orm.jpa.JpaTransactionManager.doCommit(JpaTransactionManager.java:562) ~[spring-orm-6.1.11.jar!/:6.1.11]
//		at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:795) ~[spring-tx-6.1.11.jar!/:6.1.11]
//		at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:758) ~[spring-tx-6.1.11.jar!/:6.1.11]
//		at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:663) ~[spring-tx-6.1.11.jar!/:6.1.11]
//		at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:413) ~[spring-tx-6.1.11.jar!/:6.1.11]
//		at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.1.11.jar!/:6.1.11]
//		at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.1.11.jar!/:6.1.11]
//		at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:768) ~[spring-aop-6.1.11.jar!/:6.1.11]
//		at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:720) ~[spring-aop-6.1.11.jar!/:6.1.11]
//		at com.Toou.Toou.usecase.AccountHoldingService$$SpringCGLIB$$0.execute(<generated>) ~[!/:0.0.1-SNAPSHOT]
//		at com.Toou.Toou.port.in.AccountController.holdingListStock(AccountController.java:60) ~[!/:0.0.1-SNAPSHOT]
//		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
//		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
//		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
//		at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
//		at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:926) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:831) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.1.11.jar!/:6.1.11]
//		at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar!/:6.1.11]
//		at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:389) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:904) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.26.jar!/:na]
//		at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na] = "어떤 아이디 뭔들";
//		AccountAsset accountAsset = new AccountAsset();
//		accountAsset.setId(1L);
//
//		HoldingIndividualStock stock1 = new HoldingIndividualStock();
//		HoldingIndividualStock stock2 = new HoldingIndividualStock();
//		List<HoldingIndividualStock> holdings = Arrays.asList(stock1, stock2);
//		LocalDate today = LocalDate.now();
//
//		when(accountAssetPort.findAssetByKakaoId(kakaoId)).thenReturn(accountAsset);
//		when(holdingStockPort.findAllHoldingsByAccountAssetId(accountAsset.getId())).thenReturn(
//				holdings);
//
//		AccountHoldingUseCase.Input input = new AccountHoldingUseCase.Input(kakaoId, today);
//
//		// When
//		AccountHoldingUseCase.Output output = accountHoldingUseCase.execute(input);
//
//		// Then
//		assertNotNull(output);
//		assertEquals(holdings.size(), output.getHoldings().size());
//		assertTrue(output.getHoldings().containsAll(holdings));
//		verify(accountAssetPort, times(1)).findAssetByKakaoId(kakaoId);
//		verify(holdingStockPort, times(1)).findAllHoldingsByAccountAssetId(accountAsset.getId());
//	}
//}
